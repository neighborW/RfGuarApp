package com.rifeng.p2p.entity;


import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

//联合主键
@Entity(indexes = {@Index(value = "id DESC,  tempTestId DESC", unique = true)})
public class PipeDiagramModel {

    @Id(autoincrement = true)
    public Long pipeDiagramId;

    public long id;

    String tempTestId;
    /**
     * original path
     */
    private String path;

    /**
     * The real path，But you can't get access from AndroidQ
     */
    private String realPath;

    /**
     * # Check the original button to get the return value
     * original path
     */
    private String originalPath;
    /**
     * compress path
     */
    private String compressPath;
    /**
     * cut path
     */
    private String cutPath;

    /**
     * Note: this field is only returned in Android Q version
     * <p>
     * Android Q image or video path
     */
    private String androidQToPath;
    /**
     * video duration
     */
    private long duration;
    /**
     * If the selected
     * # Internal use
     */
    private boolean isChecked;
    /**
     * If the cut
     */
    private boolean isCut;
    /**
     * media position of list
     */
    public int position;
    /**
     * The media number of qq choose styles
     */
    private int num;
    /**
     * The media resource type
     */
    private String mimeType;

    /**
     * Gallery selection mode
     */
    private int chooseModel;

    /**
     * If the compressed
     */
    private boolean compressed;
    /**
     * image or video width
     * <p>
     * # If zero occurs, the developer needs to handle it extra
     */
    private int width;
    /**
     * image or video height
     * <p>
     * # If zero occurs, the developer needs to handle it extra
     */
    private int height;

    /**
     * Crop the width of the picture
     */
    private int cropImageWidth;

    /**
     * Crop the height of the picture
     */
    private int cropImageHeight;

    /**
     * Crop ratio x
     */
    private int cropOffsetX;
    /**
     * Crop ratio y
     */
    private int cropOffsetY;

    /**
     * Crop Aspect Ratio
     */
    private float cropResultAspectRatio;

    /**
     * file size
     */
    private long size;

    /**
     * Whether the original image is displayed
     */
    private boolean isOriginal;

    /**
     * file name
     */
    private String fileName;

    /**
     * Parent  Folder Name
     */
    private String parentFolderName;

    /**
     * orientation info
     * # For internal use only
     */
    @Deprecated
    private int orientation = -1;

    public PipeDiagramModel(LocalMedia media, String tempTestId){

        this.id = media.getId();
        this.tempTestId = tempTestId;
        this.path =  media.getPath();
        this.realPath = media.getRealPath();
        this.originalPath = media.getOriginalPath();
        this.compressPath = media.getCompressPath();
        this.cutPath = media.getCutPath();
        this.androidQToPath = media.getAndroidQToPath();
        this.duration = media.getDuration();
        this.isChecked = media.isChecked();
        this.isCut = media.isCut();
        this.position = media.getPosition();
        this.num = media.getNum();
        this.mimeType = media.getMimeType();
        this.chooseModel = media.getChooseModel();
        this.compressed = media.isCompressed();
        this.width = media.getWidth();
        this.height = media.getHeight();
        this.cropImageWidth = media.getCropImageWidth();
        this.cropImageHeight = media.getCropImageHeight();
        this.cropOffsetX = media.getCropOffsetX();
        this.cropOffsetY = media.getCropOffsetY();
        this.cropResultAspectRatio = media.getCropResultAspectRatio();
        this.size = media.getSize();
        this.isOriginal = media.isOriginal();
        this.fileName = media.getFileName();
        this.parentFolderName = media.getParentFolderName();
        this.orientation = media.getOrientation();
    }

    @Generated(hash = 1296188397)
    public PipeDiagramModel(Long pipeDiagramId, long id, String tempTestId,
            String path, String realPath, String originalPath, String compressPath,
            String cutPath, String androidQToPath, long duration, boolean isChecked,
            boolean isCut, int position, int num, String mimeType, int chooseModel,
            boolean compressed, int width, int height, int cropImageWidth,
            int cropImageHeight, int cropOffsetX, int cropOffsetY,
            float cropResultAspectRatio, long size, boolean isOriginal,
            String fileName, String parentFolderName, int orientation) {
        this.pipeDiagramId = pipeDiagramId;
        this.id = id;
        this.tempTestId = tempTestId;
        this.path = path;
        this.realPath = realPath;
        this.originalPath = originalPath;
        this.compressPath = compressPath;
        this.cutPath = cutPath;
        this.androidQToPath = androidQToPath;
        this.duration = duration;
        this.isChecked = isChecked;
        this.isCut = isCut;
        this.position = position;
        this.num = num;
        this.mimeType = mimeType;
        this.chooseModel = chooseModel;
        this.compressed = compressed;
        this.width = width;
        this.height = height;
        this.cropImageWidth = cropImageWidth;
        this.cropImageHeight = cropImageHeight;
        this.cropOffsetX = cropOffsetX;
        this.cropOffsetY = cropOffsetY;
        this.cropResultAspectRatio = cropResultAspectRatio;
        this.size = size;
        this.isOriginal = isOriginal;
        this.fileName = fileName;
        this.parentFolderName = parentFolderName;
        this.orientation = orientation;
    }

    @Generated(hash = 910351396)
    public PipeDiagramModel() {
    }

    public Long getPipeDiagramId() {
        return this.pipeDiagramId;
    }

    public void setPipeDiagramId(Long pipeDiagramId) {
        this.pipeDiagramId = pipeDiagramId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTempTestId() {
        return this.tempTestId;
    }

    public void setTempTestId(String tempTestId) {
        this.tempTestId = tempTestId;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRealPath() {
        return this.realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getOriginalPath() {
        return this.originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getCompressPath() {
        return this.compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public String getCutPath() {
        return this.cutPath;
    }

    public void setCutPath(String cutPath) {
        this.cutPath = cutPath;
    }

    public String getAndroidQToPath() {
        return this.androidQToPath;
    }

    public void setAndroidQToPath(String androidQToPath) {
        this.androidQToPath = androidQToPath;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean getIsCut() {
        return this.isCut;
    }

    public void setIsCut(boolean isCut) {
        this.isCut = isCut;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getChooseModel() {
        return this.chooseModel;
    }

    public void setChooseModel(int chooseModel) {
        this.chooseModel = chooseModel;
    }

    public boolean getCompressed() {
        return this.compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
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

    public int getCropImageWidth() {
        return this.cropImageWidth;
    }

    public void setCropImageWidth(int cropImageWidth) {
        this.cropImageWidth = cropImageWidth;
    }

    public int getCropImageHeight() {
        return this.cropImageHeight;
    }

    public void setCropImageHeight(int cropImageHeight) {
        this.cropImageHeight = cropImageHeight;
    }

    public int getCropOffsetX() {
        return this.cropOffsetX;
    }

    public void setCropOffsetX(int cropOffsetX) {
        this.cropOffsetX = cropOffsetX;
    }

    public int getCropOffsetY() {
        return this.cropOffsetY;
    }

    public void setCropOffsetY(int cropOffsetY) {
        this.cropOffsetY = cropOffsetY;
    }

    public float getCropResultAspectRatio() {
        return this.cropResultAspectRatio;
    }

    public void setCropResultAspectRatio(float cropResultAspectRatio) {
        this.cropResultAspectRatio = cropResultAspectRatio;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean getIsOriginal() {
        return this.isOriginal;
    }

    public void setIsOriginal(boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParentFolderName() {
        return this.parentFolderName;
    }

    public void setParentFolderName(String parentFolderName) {
        this.parentFolderName = parentFolderName;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public LocalMedia getLocalMedia(){
        LocalMedia media = new LocalMedia();
        media.setId(this.id);
        media.setPath(this.path);
        media.setRealPath(this.realPath);
        media.setOriginalPath(this.originalPath);
        media.setCompressPath(this.compressPath);

        media.setCutPath(this.cutPath);
        media.setAndroidQToPath(this.androidQToPath);
        media.setDuration(this.duration);
        media.setChecked(this.isChecked);
        media.setCut(this.isCut);
        media.setPosition(this.position);
        media.setNum(this.num );
        media.setMimeType(this.mimeType);
        media.setChooseModel(this.chooseModel);
        media.setCompressed(this.compressed);
        media.setWidth( this.width);
        media.setHeight(this.height);
        media.setCropImageWidth(this.cropImageWidth);
        media.setCropImageHeight(this.cropImageHeight);
        media.setCropOffsetX(this.cropOffsetX);
        media.setCropOffsetY(this.cropOffsetY);
        media.setCropResultAspectRatio(this.cropResultAspectRatio);
        media.setSize(this.size );
        media.setOriginal(this.isOriginal);
        media.setFileName(this.fileName);
        media.setParentFolderName(this.parentFolderName);
        media.setOrientation(this.orientation);

        return media;
    }

}
