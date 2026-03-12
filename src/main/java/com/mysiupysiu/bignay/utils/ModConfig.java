package com.mysiupysiu.bignay.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue SCREENSHOTS_VIEWER_SHOW_FILE_NAME;
    public static final ForgeConfigSpec.BooleanValue SCREENSHOTS_VIEWER_SHOW_FILE_EXTENSION;
    public static final ForgeConfigSpec.BooleanValue SCREENSHOTS_VIEWER_SORT_TO_OLDEST;
    public static final ForgeConfigSpec.IntValue SCREENSHOTS_VIEWER_GAP;
    public static final ForgeConfigSpec.IntValue SCREENSHOTS_VIEWER_COLUMNS;

    public static final ForgeConfigSpec.BooleanValue FILE_CHOOSER_SHOW_HIDDEN_FILES;
    public static final ForgeConfigSpec.IntValue FILE_CHOOSER_COLUMNS;

    static {
        BUILDER.push("screenshots_viewer").translation("config.bignay.category.screenshots_viewer");

        SCREENSHOTS_VIEWER_SHOW_FILE_NAME = BUILDER
                .comment("Whether to display the file name underneath the image.")
                .translation("config.bignay.show_file_name")
                .define("showFileName", true);

        SCREENSHOTS_VIEWER_SHOW_FILE_EXTENSION = BUILDER
                .comment("If true, the file extension (e.g., .png) will be included in the displayed file name.")
                .translation("config.bignay.show_file_extension")
                .define("showFileExtension", false);

        SCREENSHOTS_VIEWER_SORT_TO_OLDEST = BUILDER
                .comment("If false, screenshots will be sorted starting from the oldest. If true, the newest ones will appear first.")
                .translation("config.bignay.sort_to_oldest")
                .define("sortToOldest", true);

        SCREENSHOTS_VIEWER_COLUMNS = BUILDER
                .comment("The number of columns to display in the screenshot viewer grid.")
                .translation("config.bignay.columns")
                .defineInRange("columns", 4, 1, 8);

        SCREENSHOTS_VIEWER_GAP = BUILDER
                .comment("The size of the spacing (gap) between image thumbnails in pixels.")
                .translation("config.bignay.gap")
                .defineInRange("gap", 8, 1, 32);

        BUILDER.pop();
        BUILDER.push("file_chooser").translation("config.bignay.category.file_chooser");

        FILE_CHOOSER_SHOW_HIDDEN_FILES = BUILDER
                .comment("Whether to show the hidden files.")
                .translation("config.bignay.show_hidden_files")
                .define("showHiddenFiles", false);

        FILE_CHOOSER_COLUMNS = BUILDER
                .comment("The number of columns to display in the file chooser.")
                .translation("config.bignay.columns")
                .defineInRange("columns", 6, 4, 7);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
