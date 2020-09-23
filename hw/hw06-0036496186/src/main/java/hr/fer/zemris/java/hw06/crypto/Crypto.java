package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Simple class which allows user to encrypt/decrypt given file using the AES
 * cryptoalgorithm and the 128-bit encryption key or calculate and check the
 * SHA-256 file digest
 * 
 * @author Matej Fureš
 *
 */
public class Crypto {
	public static void main(String[] args) {
		if (args.length == 2) {
			if (args[0].equals("checksha")) {
				Path path = Paths.get(args[1]);

				if (myIsNotDirectory((path))) {
					System.out.printf("Please provide expected sha-256 digest for hw06test.bin:%n> ");

					Scanner sc = new Scanner(System.in);
					String shaDigestInput = getInput(sc);
					sc.close();

					String shaDigestCal = null;

					try {
						shaDigestCal = calculateShaDigest(path);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.exit(1);
					}

					if (shaDigestCal.equals(shaDigestInput)) {
						System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");
					} else {
						System.out.printf(
								"Digesting completed. Digest of hw06test.bin does not match the expected digest. Digest%nwas: "
										+ shaDigestCal);
					}
				} else {
					System.out.println("Given path should exist and shouldn't be a directory.");
					System.exit(1);
				}

			} else {
				System.out.println("Invalid arguments. Program had 2 arguments, while first argument wasn't checksha");
				System.exit(1);
			}
		} else {
			if (args.length == 3) {
				boolean encrypt = false;
				if (args[0].equals("encrypt")) {
					encrypt = true;
				} else {
					if (args[0].equals("decrypt")) {

					} else {
						System.out.println(
								"Invalid arguments. Program had 3 arguments, while first argument wasn't encrypt nor decrypt.");
						System.exit(1);
					}
				}
				Path path = Paths.get(args[1]);
				Path dest = Paths.get(args[2]);

				if (myIsNotDirectory((path))) {
					if (Files.exists(dest)) {
						if (Files.isDirectory(dest)) {
							System.out.println("Destination mustn't be directory.");
							System.exit(1);
						}
					}
					Scanner sc = new Scanner(System.in);

					System.out
							.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
					byte[] password = getValidHexFromInput(sc);

					System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
					byte[] initializationVector = getValidHexFromInput(sc);

					sc.close();

					SecretKeySpec keySpec = new SecretKeySpec(password, "AES");
					AlgorithmParameterSpec paramSpec = new IvParameterSpec(initializationVector);
					Cipher cipher = null;
					try {
						cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
						cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

					} catch (Exception e) {
						System.out.println("Couldnt initialize cipher.");
						System.exit(1);
					}

					handleCiper(cipher, dest, path);
					if (encrypt) {
						System.out.println(
								"Encryption completed. Generated file " + args[2] + " based on file " + args[1]);
					} else {
						System.out.println(
								"Decryption completed. Generated file " + args[2] + " based on file " + args[1]);
					}
				} else {
					System.out.println("Given path should exist and shouldn't be a directory.");
					System.exit(1);
				}
			} else {
				System.out.println("Invalid number of arguments. Program requiers 2 or 3 arguments");
				System.exit(1);
			}
		}
	}

	/**
	 * Default size for input stream array
	 */
	private static final int DEFAULT_INPUT_SIZE = 4096;

	private static void handleCiper(Cipher cipher, Path dest, Path path) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(path));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(dest))) {
			byte[] buffer = new byte[DEFAULT_INPUT_SIZE];
			int d = 0;

			while (true) {
				d = is.read(buffer);

				if (d < 0) {
					break;
				}

				os.write(cipher.update(buffer, 0, d));
			}

			os.write(cipher.doFinal());
		} catch (Exception e) {
			throw new RuntimeException("Couldn't decrypt file.");
		}
	}

	/**
	 * Method return true if give path exists and is not directory
	 * 
	 * @param path to be checked
	 * @return true if given file exists and is not directory
	 */
	private static boolean myIsNotDirectory(Path path) {
		if (Files.exists(path)) {
			if (!Files.isDirectory(path)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Calculates digest for given file
	 * 
	 * @param path of file
	 * @return digest of given file
	 * @throws RuntimeException if something goes wrong
	 */
	private static String calculateShaDigest(Path path) {
		MessageDigest mdig = null;

		try {
			mdig = MessageDigest.getInstance("SHA-256");
		} catch (Exception e) {
			throw new RuntimeException("Something went wrong, algorithm not found!");
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buffer = new byte[DEFAULT_INPUT_SIZE];
			int d = 0;

			while (true) {
				d = is.read(buffer);

				if (d < 0) {
					break;
				}

				mdig.update(buffer, 0, d);
			}

		} catch (Exception e) {
			throw new RuntimeException("Couldn't create input stream or continue reading file.");
		}

		return Util.bytetohex(mdig.digest());
	}

	/**
	 * Gets 1 line from input and tries to convert it to byte array. If this method
	 * can't provide correct result, program shuts down.
	 * 
	 * @param scanner used for getting line
	 * @return byte representation of input line
	 */
	private static byte[] getValidHexFromInput(Scanner sc) {
		String input = getInput(sc);

		if (input.length() != 32) {
			System.out.println("Input should have 32 signs.");
			System.exit(1);
		}

		return Util.hextobyte(input);
	}

	/**
	 * Reads 1 line from System.in and returns it
	 * 
	 * @return 1 line from System.in
	 * @param scanner used for getting line
	 */
	private static String getInput(Scanner sc) {
		String input = "";
		input = sc.nextLine();
		return input;
	}
}
