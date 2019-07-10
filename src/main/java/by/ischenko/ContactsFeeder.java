package by.ischenko;

import lombok.RequiredArgsConstructor;

import java.io.*;

@RequiredArgsConstructor
public class ContactsFeeder {
	private final String firstNamesFile;
	private final String lastNamesFile;

	public void writeFullNames(TelephoneDirectory telephoneDirectory, int capacity) throws IOException {
		int i = 0;
		try (BufferedReader firstNameBufReader = new BufferedReader(new FileReader(getFileByName(firstNamesFile)))) {
			String firstName;
			while ((firstName = firstNameBufReader.readLine()) != null) {
				try (BufferedReader lastNameBufReader = new BufferedReader(new FileReader(getFileByName(lastNamesFile)))) {
					String lastName;
					while ((lastName = lastNameBufReader.readLine()) != null) {
						telephoneDirectory.put(firstName + " " + lastName, i++);
						if (i % 100_000 == 0)
							System.out.println("Processed: " + i);
						if (i == capacity)
							return;
					}
				}
			}
		}
	}

	private File getFileByName(String fileName) {
		return new File(getClass().getClassLoader().getResource(fileName).getFile());
	}
}
