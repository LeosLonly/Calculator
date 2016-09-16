package edu.bistu.computer.calculator.conversion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ConversionContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ConversionItem> ITEMS = new ArrayList<ConversionItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ConversionItem> ITEM_MAP = new HashMap<String, ConversionItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        addItem(new ConversionItem("1","Lenght","Lenght"));
        addItem(new ConversionItem("2","Quality","Quality"));
        addItem(new ConversionItem("3","Volume","Volume"));
        addItem(new ConversionItem("4","Time","Time"));
    }

    private static void addItem(ConversionItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ConversionItem createConversionItem(int position) {
        return new ConversionItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ConversionItem {
        public final String id;
        public final String content;
        public final String details;

        public ConversionItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
