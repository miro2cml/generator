package ch.ost.rj.sa.miro2cml.data_access.miro_model;

public class WidgetObject {
    private String type;
    private double x;
    private double y;
    private double scale;
    private int width;
    private int height;
    private String text;
    private String id;
    private Style style;
    private String modifiedAt;
    private ModifiedBy modifiedBy;
    private String createdAt;
    private CreatedBy createdBy;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getScale() {
        return this.scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Style getStyle() {
        return this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getModifiedAt() {
        return this.modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public ModifiedBy getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(ModifiedBy modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public CreatedBy getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public class Style {
        private String backgroundColor;

        private String fontFamily;

        private int fontSize;

        private String textAlign;

        private String textAlignVertical;

        public String getBackgroundColor() {
            return this.backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getFontFamily() {
            return this.fontFamily;
        }

        public void setFontFamily(String fontFamily) {
            this.fontFamily = fontFamily;
        }

        public int getFontSize() {
            return this.fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public String getTextAlign() {
            return this.textAlign;
        }

        public void setTextAlign(String textAlign) {
            this.textAlign = textAlign;
        }

        public String getTextAlignVertical() {
            return this.textAlignVertical;
        }

        public void setTextAlignVertical(String textAlignVertical) {
            this.textAlignVertical = textAlignVertical;
        }
    }

    public class ModifiedBy {
        private String type;

        private String name;

        private String id;

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class CreatedBy {
        private String type;

        private String name;

        private String id;

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
