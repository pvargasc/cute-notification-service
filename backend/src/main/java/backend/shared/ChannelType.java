package backend.shared;

public enum ChannelType {
    EMAIL,
    SMS,
    PUSH_NOTIFICATION;

    public static boolean contains(ChannelType channelType) {
        for (ChannelType type : ChannelType.values()) {
            if (type == channelType) {
                return true;
            }
        }

        return false;
    }
}
