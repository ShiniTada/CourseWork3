package by.bsuir.client.entity;

public enum TrainTypeEnum {

    HIGH_SPEED("высокоскоростной"),
    SPEEDY("скоростной"),
    FAST("скорый"),
    BRANDED("фирменный");
    private String value;

    private TrainTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Get value of Train flight and return {@TrainTypeEnum type}
     * @param typeValue - value of Train flight type
     * @return TrainTypeEnum
     */
    public  static TrainTypeEnum findEnum(String typeValue) {
        if (typeValue.equals(TrainTypeEnum.HIGH_SPEED.getValue())) {
            return TrainTypeEnum.HIGH_SPEED;
        }
        if (typeValue.equals(TrainTypeEnum.SPEEDY.getValue())) {
            return TrainTypeEnum.SPEEDY;
        }
        if (typeValue.equals(TrainTypeEnum.FAST.getValue())) {
            return TrainTypeEnum.FAST;
        }
        if (typeValue.equals(TrainTypeEnum.BRANDED.getValue())) {
            return TrainTypeEnum.BRANDED;
        }
        return null;
    }
}
