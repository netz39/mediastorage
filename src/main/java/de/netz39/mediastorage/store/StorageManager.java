package de.netz39.mediastorage.store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.netz39.mediastorage.MediaItem;

public enum StorageManager {
	INSTANCE;

	private File root = null;

	public synchronized void setRoot(File root) {
		if (this.root != null)
			throw new IllegalStateException("FS root can only be set once!");

		if (root == null)
			throw new NullPointerException("Root argument must not be null!");

		this.root = root;
	}

	public String store(MediaItem item, InputStream is) throws IOException {
		if (item == null)
			throw new NullPointerException("item argument must not be null!");

		int _extid = item.getOriginalName().lastIndexOf('.');
		String extension = _extid == -1 ? "" : item.getOriginalName()
				.substring(_extid);

		final File tmp = File.createTempFile("mediaupload-", extension);

		final MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		final OutputStream os = new FileOutputStream(tmp);

		final byte[] buffer = new byte[1024];
		int nread;
		while ((nread = is.read(buffer)) != -1) {
			md.update(buffer, 0, nread);
			os.write(buffer, 0, nread);
		}

		os.close();

		final byte[] mdbytes = md.digest();

		// convert the byte to hex format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mdbytes.length; i++)
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));

		return sb.toString();

	}
}
