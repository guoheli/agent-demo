package biz;

import com.google.common.collect.Lists;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author
 * 1、 https://blog.51cto.com/u_13050237/4808437
 * 2、https://zhuanlan.zhihu.com/p/85895180 &https://blog.csdn.net/qq_40985985/article/details/111240098
 *
 * 3、依赖处理：
 * https://wenku.baidu.com/view/992bab57f142336c1eb91a37f111f18583d00c79.html
 *
 * 4、https://www.cnblogs.com/letben/p/5662619.html
 */
public class VideoLocal {

	/**
	 * 视频文件指定时间段的帧截取
	 * @param file
	 * @param start
	 * @param end
	 */
	public static List<File> videoIntercept(File file, Integer start, Integer end) {
		Frame frame = null;
		List<File> files = Lists.newArrayList();
		FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(file);
		String filePath = "C:\\Users\\zengxiaoning\\Desktop\\images\\";
		String fileTargetName = "movie";
		try {
			fFmpegFrameGrabber.start();
			int ftp = fFmpegFrameGrabber.getLengthInFrames();
			System.out.println("开始视频提取帧");
			for (int i=0 ; i < ftp ; i++){
				if( i >= start && i <= end){
					frame = fFmpegFrameGrabber.grabImage();
					doExecuteFrame(frame, filePath, fileTargetName, i ,files);
				}
			}
			System.out.println("============运行结束============");
			fFmpegFrameGrabber.stop();
		} catch (IOException E) {
//            Loggers.ERROR.error("视频抽帧异常", e);
		}
		return  files;
	}
	public static void doExecuteFrame(Frame frame, String targetFilePath, String targetFileName, int index ,List<File> files) {
		if ( frame == null || frame.image == null) {
			return;
		}
		Java2DFrameConverter converter = new Java2DFrameConverter();
		String imageMat = "jpg";
		String fileName = targetFilePath + targetFileName + "_" + index + "." + imageMat;
		BufferedImage bi = converter.getBufferedImage(frame);
		File output = new File(fileName);
		files.add(output);
		try{
			ImageIO.write(bi, imageMat, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		List<File> files = videoIntercept(new File("C:\\Users\\zengxiaoning\\Desktop\\d9dd8e571437b9add9f0b2e4bdd95b9b.mp4"), 10, 20);
		System.out.println(files);
	}

}
