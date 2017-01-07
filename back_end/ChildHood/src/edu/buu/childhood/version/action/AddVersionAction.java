package edu.buu.childhood.version.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import edu.buu.childhood.common.C;
import edu.buu.childhood.version.service.VersionService;

public class AddVersionAction extends ActionSupport {
	/**
	 * AddVersionAction序列化ID
	 */
	private static final long serialVersionUID = 5352794067111787459L;
	private String version;
	private String note;
	private String system;

	private File uploadPkg;
	private String uploadPkgFileName;
	private String uploadPkgContentType;

	private VersionService versionService;

	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public File getUploadPkg() {
		return uploadPkg;
	}

	public void setUploadPkg(File uploadPkg) {
		this.uploadPkg = uploadPkg;
	}

	public String getUploadPkgFileName() {
		return uploadPkgFileName;
	}

	public void setUploadPkgFileName(String uploadPkgFileName) {
		this.uploadPkgFileName = uploadPkgFileName;
	}

	public String getUploadPkgContentType() {
		return uploadPkgContentType;
	}

	public void setUploadPkgContentType(String uploadPkgContentType) {
		this.uploadPkgContentType = uploadPkgContentType;
	}

	@Override
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session.getAttribute("userName") != null) {
			try {
				String root = ServletActionContext.getServletContext()
						.getRealPath("/packages");

				File file = new File(root, uploadPkgFileName);
				if (file.exists()) {
					throw new Exception("file exists!");
				}

				InputStream inputStream = new FileInputStream(uploadPkg);

				OutputStream outputStream = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int count, i;
				while (-1 != (count = inputStream
						.read(buffer, 0, buffer.length))) {
					for (i = 0; i < count; i++) {
						outputStream.write(buffer[i]);
					}
				}
				outputStream.close();
				inputStream.close();

				String packageUrl = C.def.DOMAIN + "packages/"
						+ uploadPkgFileName;

				versionService
						.addVersionInfo(version, packageUrl, note, system);
				session.setAttribute("updateSuccess", true);
			} catch (Exception e) {
				session.setAttribute("updateSuccess", false);
				e.printStackTrace();
			}
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
}
