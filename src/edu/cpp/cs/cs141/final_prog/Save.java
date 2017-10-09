package edu.cpp.cs.cs141.final_prog;
/**
 * @author Mostafa, Charlie, Saul, and Jae.
 *
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Save {
	public void writeSave(UserInterface userInt) {
		try {
			FileOutputStream fos = new FileOutputStream("gameSave.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(userInt);

			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static UserInterface readSave() {
		UserInterface userInt = null;

		try {
			FileInputStream fis = new FileInputStream("gameSave.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);

			userInt = (UserInterface) ois.readObject();

			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("No previous game load files exist.");
		}

		return userInt;
	}
}

