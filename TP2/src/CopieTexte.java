import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CopieTexte {

	private File source;
	private File dest;

	public boolean init(String source, String dest) {
		this.source = new File(source);
		this.dest = new File(dest);
		return this.source.canRead() && !this.dest.exists();
	}

	public boolean copie() {
		boolean retour = true;
		BufferedReader sourceFile = null;
		PrintWriter destFile = null;
		try {
			sourceFile = new BufferedReader(new FileReader(this.source));
			destFile = new PrintWriter(this.dest);

			String buffer;
			while ((buffer = sourceFile.readLine()) != null) {
				destFile.println(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sourceFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			destFile.close();
		}
		return retour;
	}

	public static void main(String[] args) {
		CopieTexte cp = new CopieTexte();
		if (!cp.init(args[0], args[1])) {
			System.out.println("Erreur");
		} else {
			if (cp.copie())
				System.out.println("Copie reussie");
		}
	}
}
