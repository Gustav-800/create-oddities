package dev.createoddities.module.typewriter;

import com.mojang.blaze3d.platform.InputConstants;
import dev.createoddities.config.OdditiesConfig;
import java.lang.reflect.Method;
import java.util.Set;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Typewriter keyguard module for Create Oddities.
 *
 * <p>Decides when keyboard input belongs exclusively to the typewriter and hands
 * the event to Simulated's own handler.</p>
 *
 * <p>The Simulated classes are accessed reflectively on purpose: this module
 * only needs two public members, and reflection keeps the build independent
 * of Simulated's jar. If Simulated is absent or changes those members, the
 * module fails open (normal keyboard behaviour) instead of crashing the game.</p>
 */
public final class TypewriterKeyguard {
    private static final Logger LOGGER = LoggerFactory.getLogger("Create Oddities");

    private static final String HANDLER_CLASS =
            "dev.simulated_team.simulated.content.blocks.redstone.linked_typewriter.LinkedTypewriterInteractionHandler";
    private static final String ACTIVE = "ACTIVE";
    private static final String BINDING_FROM_ITEM = "BINDING_FROM_ITEM";

    /** GLFW_KEY_ESCAPE - forwarded to Simulated so it can disconnect cleanly. */
    private static final int GLFW_KEY_ESCAPE = 256;
    /** GLFW_KEY_F3 - held down while a debug combo is being used. */
    private static final int GLFW_KEY_F3 = 292;
    /** GLFW_KEY_F11 - fullscreen is toggled by this mod while typing. */
    private static final int GLFW_KEY_F11 = 300;

    /**
     * The 68 keyboard keys the typewriter can bind, in the same set exposed by
     * Simulated's key-editor screen. Keys outside this set (F1-F12, Insert,
     * Home, numpad, PrintScreen, media keys, etc.) are left to vanilla.
     */
    private static final Set<Integer> TYPEWRITER_KEYS = Set.of(
            32, 39, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
            59, 61, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78,
            79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 96,
            257, 258, 259, 261, 262, 263, 264, 265, 266, 267, 269, 280,
            340, 341, 342, 343, 344, 345, 346, 348
    );
    /** GLFW_PRESS - only toggle fullscreen on the initial press, not on release/repeat. */
    private static final int GLFW_PRESS = 1;

    private static boolean resolutionAttempted;
    private static Method getModeMethod;
    private static Method onKeyPressMethod;
    private static boolean reflectionBroken;

    private TypewriterKeyguard() {
    }

    /** Called by the module manager after config load. */
    public static void initialize() {
        // Reflection is resolved lazily on the first key event so a missing
        // Simulated install never blocks startup.
    }

    /** Whether Simulated's typewriter handler can be resolved. */
    public static boolean isAvailable() {
        return resolveHandler();
    }

    public static boolean shouldCapture(long windowHandle, int key) {
        if (!OdditiesConfig.TYPEWRITER_ENABLED.get()) {
            return false;
        }
        // Esc is forwarded to Simulated, which disconnects the user and exits the typewriter.
        if (key == GLFW_KEY_ESCAPE) {
            return isTypewriterActive(windowHandle);
        }
        // Only the typewriter's 68 bindable keys belong to it. Everything else (F1-F12,
        // Insert, Home, numpad, PrintScreen, media keys, ...) is left to vanilla.
        if (!TYPEWRITER_KEYS.contains(key)) {
            return false;
        }
        // While F3 is held, the second key belongs to a vanilla debug combo. Let it through.
        if (InputConstants.isKeyDown(windowHandle, GLFW_KEY_F3)) {
            return false;
        }
        return isTypewriterActive(windowHandle);
    }

    /**
     * Handles F11 while the typewriter owns the keyboard.
     *
     * <p>Simply letting F11 through is not enough: Simulated's own key handler
     * can still consume it after our HEAD injection. Toggle the window here and
     * cancel the event so fullscreen works regardless of what runs later.</p>
     *
     * @return true when the event was handled and must be cancelled
     */
    public static boolean handleFullscreenKey(long windowHandle, int key, int action) {
        if (!OdditiesConfig.TYPEWRITER_ENABLED.get()) {
            return false;
        }
        if (key != GLFW_KEY_F11 || !isTypewriterActive(windowHandle)) {
            return false;
        }
        if (action == GLFW_PRESS) {
            Minecraft.getInstance().getWindow().toggleFullScreen();
        }
        return true;
    }

    private static boolean isTypewriterActive(long windowHandle) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.screen != null) {
            return false;
        }
        if (windowHandle != minecraft.getWindow().getWindow()) {
            return false;
        }
        if (!resolveHandler()) {
            return false;
        }

        try {
            Object mode = getModeMethod.invoke(null);
            if (mode == null) {
                return false;
            }
            String modeName = mode instanceof Enum<?> enumeration ? enumeration.name() : mode.toString();
            return ACTIVE.equals(modeName) || BINDING_FROM_ITEM.equals(modeName);
        } catch (ReflectiveOperationException | RuntimeException | LinkageError exception) {
            disableReflection("read typewriter mode", exception);
            return false;
        }
    }

    public static void forwardToTypewriter(int key, int scanCode, int action, int modifiers) {
        if (!resolveHandler()) {
            return;
        }
        try {
            onKeyPressMethod.invoke(null, key, scanCode, action, modifiers);
        } catch (ReflectiveOperationException | RuntimeException | LinkageError exception) {
            disableReflection("forward key event", exception);
        }
    }

    private static boolean resolveHandler() {
        if (reflectionBroken) {
            return false;
        }
        if (!resolutionAttempted) {
            resolutionAttempted = true;
            try {
                Class<?> handler = Class.forName(HANDLER_CLASS);
                getModeMethod = handler.getMethod("getMode");
                onKeyPressMethod = handler.getMethod("onKeyPress", int.class, int.class, int.class, int.class);
            } catch (ClassNotFoundException | NoClassDefFoundError exception) {
                reflectionBroken = true;
                LOGGER.info("Simulated is not installed; the typewriter keyguard module is disabled.");
            } catch (ReflectiveOperationException | RuntimeException | LinkageError exception) {
                disableReflection("find Simulated's typewriter handler", exception);
            }
        }
        return getModeMethod != null && onKeyPressMethod != null;
    }

    private static void disableReflection(String action, Throwable throwable) {
        if (!reflectionBroken) {
            reflectionBroken = true;
            LOGGER.warn(
                    "Create Oddities could not {}; the typewriter keyguard module is disabled. "
                            + "This usually means the installed Create: Aeronautics / Simulated version is not supported.",
                    action,
                    throwable
            );
        }
    }
}
