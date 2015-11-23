package uk.ac.gla.bikepool;

public class User {

    private int mId;
    private String mName;
    private String mThumbnailUrl;
    private Double mRank;
    private int[] mPools;

    public User() {}

    public User(String name, String thumbnailUrl) {
        mId = 0;
        mName = name;
        mThumbnailUrl = thumbnailUrl;
        mRank = 0.0;
        mPools = null;
    }

    public int getId() {return mId;}

    public void setId(int id) {mId = id;}

    public String getName() {return mName;}

    public void setName(String name) {mName = name;}

    public String getThumbnailUrl() {return mThumbnailUrl;}

    public void setThumbnailUrl(String thumbnailUrl) {mThumbnailUrl = thumbnailUrl;}

    public Double getRank() {return mRank;}

    public void setRank(Double rank) {mRank = rank;}

    public int[] getPools() {return mPools;}

    public void setPools(int[] pools) {mPools = pools;}
}
