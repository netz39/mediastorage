package de.netz39.mediastorage.http;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = -4562058556833725285L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// check if content is multipart
		if (!ServletFileUpload.isMultipartContent(request)) {
			response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"Unsupported media type, expecting multipart form content!");
			return;
		}

		// get the upload hander
		final FileCleaningTracker fileCleaningTracker = FileCleanerCleanup
				.getFileCleaningTracker(request.getSession()
						.getServletContext());
		final DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setFileCleaningTracker(fileCleaningTracker);
		final ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);

			for (final FileItem item : items) {
				// ignore form fields
				if (item.isFormField())
					continue;
				
				final String name = item.getName();
			}
		} catch (FileUploadException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Internal Server Error: FileUpload " + e.getMessage());
		}

	}
}
