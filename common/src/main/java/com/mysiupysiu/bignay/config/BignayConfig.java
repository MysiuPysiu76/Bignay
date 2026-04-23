package com.mysiupysiu.bignay.config;

import java.util.ArrayList;
import java.util.List;

public class BignayConfig {

    public static final List<ConfigCategory> CATEGORIES = new ArrayList<>();
    public static Runnable saveCallback;

    public static final Biomes biomes = new Biomes();
    public static final Containers containers = new Containers();
    public static final Files files = new Files();
    public static final Screenshots screenshots = new Screenshots();

    public static final class Biomes extends ConfigCategory {

        public final BooleanOption verdantForest = add(new BooleanOption.Builder("verdantForest", false)
                .comment("Determines whether the verdant forest biome should be generated on the world")
                .translation("config.bignay.generate_verdant_forest")
                .build());

        private Biomes() {
            super("biomes");
        }
    }

    public static final class Containers extends ConfigCategory {

        public final EnumOption<SortMode> sortMode = add(new EnumOption.Builder<>("sortMode", SortMode.QUANTITY)
                .comment("A method for sorting items such as chests and inventory\nAvailable values: ALPHABETICAL, QUANTITY")
                .translation("config.bignay.container_sort_mode")
                .build());

        public final BooleanOption showSortInventory = add(new BooleanOption.Builder("showSortInventory", true)
                .comment("Displays the inventory sort button")
                .translation("config.bignay.container_show_sort_inventory")
                .build());

        public final BooleanOption showSortContainer = add(new BooleanOption.Builder("showSortContainer", true)
                .comment("Displays the chest sort button")
                .translation("config.bignay.container_show_sort_container")
                .build());

        public final BooleanOption showTransferToInventory = add(new BooleanOption.Builder("showTransferToInventory", true)
                .comment("Displays a button to quickly move items to your inventory")
                .translation("config.bignay.container_transfer_to_inventory")
                .build());

        public final BooleanOption showTransferToContainer = add(new BooleanOption.Builder("showTransferToContainer", true)
                .comment("Displays a button to quickly move items to a container")
                .translation("config.bignay.container_transfer_to_container")
                .build());

        private Containers() {
            super("containers");
        }
    }

    public static final class Files extends ConfigCategory {

        public final IntOption columns = add(new IntOption.Builder("columns", 6)
                .comment("The number of columns to display in the file chooser.")
                .translation("config.bignay.columns")
                .range(4, 7)
                .build());

        public final BooleanOption showHidden = add(new BooleanOption.Builder("showHidden", false)
                .comment("Whether to show the hidden files.")
                .translation("config.bignay.show_hidden_files")
                .build());

        private Files() {
            super("file_chooser");
        }
    }

    public static final class Screenshots extends ConfigCategory {

        public final BooleanOption showFileName = add(new BooleanOption.Builder("showFileName", true)
                .comment("Whether to display the file name underneath the image.")
                .translation("config.bignay.show_file_name")
                .build());

        public final BooleanOption showFileExtension = add(new BooleanOption.Builder("showFileExtension", false)
                .comment("If true, the file extension (e.g., .png) will be included in the displayed file name.")
                .translation("config.bignay.show_file_extension")
                .build());

        public final BooleanOption sortToOldest = add(new BooleanOption.Builder("sortToOldest", true)
                .comment("If false, screenshots will be sorted starting from the oldest. If true, the newest ones will appear first.")
                .translation("config.bignay.sort_to_oldest")
                .build());

        public final IntOption columns = add(new IntOption.Builder("columns", 5)
                .comment("The number of columns to display in the screenshot viewer grid.")
                .translation("config.bignay.columns")
                .range(1, 8)
                .build());

        public final IntOption gap = add(new IntOption.Builder("gap", 8)
                .comment("The size of the spacing (gap) between image thumbnails in pixels.")
                .translation("config.bignay.gap")
                .range(1, 32)
                .build());

        private Screenshots() {
            super("screenshots_viewer");
        }
    }

    public static void save() {
        if (saveCallback != null) saveCallback.run();
    }
}
