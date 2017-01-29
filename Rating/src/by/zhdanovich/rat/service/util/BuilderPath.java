package by.zhdanovich.rat.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.zhdanovich.rat.service.exception.ServiceException;

/**
 * Class {@code BuilderPath} is used to create addresses downloaded save file at
 * runtime.
 * 
 * @author Anna
 *
 */
public class BuilderPath {
	private static Logger log = LogManager.getLogger(BuilderPath.class);

	/**
	 * It saves the file on the server and receives the path to it.
	 * 
	 * @param filePart
	 * @param path
	 * @return
	 */
	public String putImage(Part filePart, String path)throws ServiceException{
		String fileName = null;
		try {
			if (filePart != null) {
				String header = filePart.getHeader(ServiceParameter.CONST_HEADER);
				
				fileName = takeName(header);
				OutputStream out = null;
				InputStream filecontent = null;
				try {
					out = new FileOutputStream(new File(path + ServiceParameter.SLASH + fileName));
					filecontent = filePart.getInputStream();
					int read = 0;
					final byte[] bytes = new byte[1024];
					while ((read = filecontent.read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}
				} finally {
					if (out != null) {
						out.close();
					}
					if (filecontent != null) {
						filecontent.close();
					}
				}
			}else {
				throw new ServiceException("Wrong input data");
			}

		} catch (IOException e) {
			log.error("Error loading file", e);
		}
		return ServiceParameter.REAL_PATH + fileName;
	}

	/**
	 * Separation from a common path and name and type of the file.
	 * 
	 * @param item the full address of the directory where to save the file
	 * @return
	 */
	public String takeName(String item) {
		Pattern pattern = Pattern.compile(ServiceParameter.REGEX_WORD);
		Matcher matcher = pattern.matcher(item);
		String typeOfFile = null;
		if (matcher.find()) {
			typeOfFile = matcher.group();
			return typeOfFile;
		}
		return null;
	}
}
