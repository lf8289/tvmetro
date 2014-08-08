package com.example.tvmetro.model;

import java.util.HashMap;

import com.example.tvmetro.R;

public class LayoutFactory {
    private static LayoutFactory sInstance;

    private HashMap<String, Integer> mLayoutMap;

    public enum BgTypeEnum {
        IMG("img"), COLOR("color"), ACTION("action"), VIDEO("video"), APP("app"), ADSHOW(
                "adshow");

        public final String value;

        private BgTypeEnum(String value) {
            this.value = value;
        }

        public static BgTypeEnum fromValue(String value) {
            for (BgTypeEnum style : BgTypeEnum.values()) {
                if (value.equalsIgnoreCase(style.value)) {
                    return style;
                }
            }
            return null;
        }
    }

    private LayoutFactory() {
        mLayoutMap = new HashMap<String, Integer>();

        mLayoutMap.put("img",
                Integer.valueOf(R.layout.layout_templet_h_large_rect));

        mLayoutMap.put("action",
                Integer.valueOf(R.layout.layout_templet_action_rect));

        mLayoutMap.put("adshow",
                Integer.valueOf(R.layout.layout_templet_h_largeadshow_rect));

        // TODO: to add these layout
        mLayoutMap.put("color",
                Integer.valueOf(R.layout.layout_templet_h_large_rect));
        mLayoutMap.put("video",
                Integer.valueOf(R.layout.layout_templet_h_large_rect));
        mLayoutMap.put("app",
                Integer.valueOf(R.layout.layout_templet_h_large_rect));
    }

    public static LayoutFactory getInstance() {
        if (sInstance == null) {
            sInstance = new LayoutFactory();
        }

        return sInstance;
    }

    public void addLayout(String type, int resource) {
        mLayoutMap.put(type, resource);
    }

    public int getLayout(String type) {
        return mLayoutMap.get(type);
    }
}
