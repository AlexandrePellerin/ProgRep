import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopieBinaire {

	private File source;
	private File dest;

	public boolean init(String source, String dest) {
		this.source = new File(source);
		this.dest = new File(dest);
		return this.source.canRead() && !this.dest.exists();
	}

	public boolean copie() {
		boolean retour = true;
		InputStream sourceFile = null;
		OutputStream destFile = null;
		try {
			sourceFile = new FileInputStream(this.source);
			destFile = new FileOutputStream(this.dest);

			byte buffer[] = new byte[(int) this.source.length() + 1];
			int nbLecture;
			while ((nbLecture = sourceFile.read(buffer)) != -1) {
				destFile.write(buffer, 0, nbLecture);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sourceFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				destFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retour;
	}
	
	public static void main(String [] args){
		CopieBinaire cp = new CopieBinaire();
		if(!cp.init(args[0], args[1])){
			System.out.println("Erreur");
		}
		else{
			if(cp.copie())
				System.out.println("Copie reussie");
		}
	}
}
