package uk.ac.gla.bikepool;

public class User {

    private String mName;
    private String mThumbnailUrl;
    private Double mRank;
    private int[] mPools;

    public User(String name, String thumbnailUrl) {
        mName = name;
        mThumbnailUrl = thumbnailUrl;
        mRank = 0.0;
        mPools = null;
    }

    public String getName() {return mName;}

    public void setmName(String name) {mName = name;}

    public String getThumbnailUrl() {return mThumbnailUrl;}

    public void setThumbnailUrl(String thumbnailUrl) {mThumbnailUrl = thumbnailUrl;}

    public Double getRank() {return mRank;}

    public void setRank(Double rank) {mRank = rank;}

    public int[] getPools() {return mPools;}

    public void setPools(int[] pools) {mPools = pools;}
}
