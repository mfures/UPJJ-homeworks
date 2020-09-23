package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Simple LSystemBuilder implementation that provides functionality for working
 * with LSystemBuilder
 * 
 * @author Matej Fureš
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	/**
	 * Defines unit length, initially 0.1
	 */
	private double unitLength = 0.1;

	/**
	 * Defines unit length degree scaler, initially 0.1
	 */
	private double unitLengthDegreeScaler = 1;

	/**
	 * Defines origin of picture, initially 0,0
	 */
	private Vector2D origin = new Vector2D(0, 0);

	/**
	 * Defines angle, initially 0
	 */
	private double angle = 0;

	/**
	 * Defines axiom, initially ""
	 */
	private String axiom = "";

	/**
	 * Dictionary that tracks productions
	 */
	private Dictionary<Character, String> productions;

	/**
	 * Dictionary that tracks commands
	 */
	private Dictionary<Character, Command> commands;

	/**
	 * Constructor. Initializes dictionaries
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<>();
		commands = new Dictionary<>();
	}

	/**
	 * Factory function that creates LSystem
	 * 
	 * @return
	 */
	@Override
	public LSystem build() {
		return new LSystem() {
			/**
			 * Generates chosen generation. If generation is less than 0, an exception is
			 * thrown
			 * 
			 * @return chosen generation
			 * @throws IllegalArgumentException if arg0 is less than 0
			 */
			@Override
			public String generate(int arg0) {
				if (arg0 < 0) {
					throw new IllegalArgumentException();
				}

				String currentGen = axiom;
				StringBuilder nextGen = new StringBuilder();

				for (int i = 0; i < arg0; i++) {
					for (int j = 0; j < currentGen.length(); j++) {
						String prod = productions.get(currentGen.charAt(j));
						nextGen.append(prod == null ? currentGen.charAt(j) : prod);
					}
					currentGen = nextGen.toString();
					nextGen.setLength(0);
				}

				return currentGen;
			}

			/**
			 * Draws chosen generation, arg0 can't be negative, arg1 can't be null,
			 * otherwise an exception is thrown
			 * 
			 * @throws NullPointerException     if arg1 is null
			 * @throws IllegalArgumentException if arg0 is less than 0
			 */
			@Override
			public void draw(int arg0, Painter arg1) {
				Objects.requireNonNull(arg1);

				if (arg0 < 0) {
					throw new IllegalArgumentException();
				}

				Context context = new Context();
				TurtleState state = new TurtleState(origin.copy(), new Vector2D(1, 0).rotated(angle), Color.BLUE,
						unitLength * Math.pow(unitLengthDegreeScaler, arg0));

				context.pushState(state);

				char[] generation = generate(arg0).toCharArray();

				for (int i = 0; i < generation.length; i++) {
					Command currentCommand = commands.get(generation[i]);

					if (currentCommand != null) {
						currentCommand.execute(context, arg1);
					}
				}
			}
		};
	}

	/**
	 * Configures this LSystemBuilder form String array, if any of the strings is
	 * null, an exception is thrown. If strings aren't in correct format, an
	 * exception is thrown.
	 * 
	 * @return this LSystemBuilder
	 * @throws NullPointerException     if arg0 is null, or some string in it is
	 *                                  null
	 * @throws IllegalArgumentException if strings aren't in correct format
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		Objects.requireNonNull(arg0);

		for (String s : arg0) {
			Objects.requireNonNull(s);
			if (s.isEmpty()) {
				continue;
			}

			String[] arguments = s.split("\\s+");

			switch (arguments[0]) {
			case "origin":
				if (arguments.length != 3) {
					throw new IllegalArgumentException();
				}

				double xCord = getIfDouble(arguments[1]);
				double yCord = getIfDouble(arguments[2]);

				origin = new Vector2D(xCord, yCord);
				break;

			case "angle":
				if (arguments.length != 2) {
					throw new IllegalArgumentException();
				}

				double angleInDegree = getIfDouble(arguments[1]);

				angle = angleInDegree * Math.PI / 180;
				break;

			case "unitLength":
				if (arguments.length != 2) {
					throw new IllegalArgumentException();
				}

				double newUnitLength = getIfDouble(arguments[1]);

				unitLength = newUnitLength;
				break;

			case "unitLengthDegreeScaler":
				double numerator;
				double denominator;

				if (arguments.length == 2) {
					String[] numbers = arguments[1].split("/");
					if (numbers.length != 2) {
						throw new IllegalArgumentException();
					}

					numerator = getIfDouble(numbers[0]);
					denominator = getIfDouble(numbers[1]);
				} else {
					if (arguments.length == 3) {
						if (arguments[1].charAt(arguments[1].length() - 1) == '/') {
							numerator = getIfDouble(arguments[1].substring(0, arguments[1].length() - 1));
							denominator = getIfDouble(arguments[2]);
						} else {
							if (arguments[2].charAt(0) != '/') {
								throw new IllegalArgumentException();
							}
							numerator = getIfDouble(arguments[1]);
							denominator = getIfDouble(arguments[2].substring(1));
						}
					} else {
						if (arguments.length != 4) {
							throw new IllegalArgumentException();
						}

						numerator = getIfDouble(arguments[1]);
						denominator = getIfDouble(arguments[3]);
					}
				}

				if (denominator == 0) {
					throw new IllegalArgumentException();
				}

				unitLengthDegreeScaler = numerator / denominator;
				break;

			case "command":
				if (arguments.length < 3) {
					throw new IllegalArgumentException();
				}

				if (arguments[1].length() != 1) {
					throw new IllegalArgumentException();
				}

				Character key = arguments[1].charAt(0);
				Command com = getIfCommand(Arrays.copyOfRange(arguments, 2, arguments.length));

				commands.put(key, com);
				break;

			case "axiom":
				if (arguments.length != 2) {
					throw new IllegalArgumentException();
				}

				axiom = arguments[1];
				break;

			case "production":
				if (arguments.length != 3) {
					throw new IllegalArgumentException();
				}

				if (arguments[1].length() != 1) {
					throw new IllegalArgumentException();
				}

				Character keyP = arguments[1].charAt(0);

				if (productions.get(keyP) != null) {
					throw new IllegalArgumentException();
				}

				productions.put(keyP, arguments[2]);
				break;

			default:
				throw new IllegalArgumentException();
			}
		}

		return this;
	}

	/**
	 * Tries to parse string to Command, if it can't, throws an exception
	 * 
	 * @param s String to be parsed
	 * @return parsed string as command
	 * @throws IllegalArgumentException if s can't be parsed as double
	 */
	private Command getIfCommand(String... s) {
		if (s.length == 1) {
			switch (s[0]) {
			case "push":
				return new PushCommand();

			case "pop":
				return new PopCommand();
			}

			throw new IllegalArgumentException();
		}

		if (s.length == 2) {
			switch (s[0]) {
			case "draw":
				return new DrawCommand(getIfDouble(s[1]));

			case "skip":
				return new SkipCommand(getIfDouble(s[1]));

			case "scale":
				return new ScaleCommand(getIfDouble(s[1]));

			case "rotate":
				return new RotateCommand(getIfDouble(s[1]));

			case "color":
				try {
					return new ColorCommand(Color.decode("#" + s[1]));
				} catch (Exception e) {
					throw new IllegalArgumentException();
				}
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Tries to parse string to double, if it can't, throws an exception
	 * 
	 * @param s String to be parsed
	 * @return parsed string as double
	 * @throws IllegalArgumentException if s can't be parsed as double
	 */
	private double getIfDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Tries to register command, if it can't, an exception is thrown
	 * 
	 * @param arg0 key of command
	 * @param arg1 string to be parsed
	 * @return this LSystemBuilder
	 * @throws IllegalArgumentException if command can't be parsed
	 * @throws NullPointerException     if arg1 is null
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		Objects.requireNonNull(arg1);

		commands.put(arg0, getIfCommand(arg1.split("\\s+")));
		return this;
	}

	/**
	 * Registers a production, if arg1 is null, exception is thrown
	 * 
	 * @param arg0 Symbol that is in production
	 * @param arg1 Production
	 * @return this LSystemBuilder
	 * @throws NullPointerException if arg1 is null
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		Objects.requireNonNull(arg1);

		if (productions.get(arg0) != null) {
			throw new IllegalArgumentException();
		}

		productions.put(arg0, arg1);
		return this;
	}

	/**
	 * Sets angle
	 * 
	 * @param arg0 angle in degree
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0 * Math.PI / 180;

		return this;
	}

	/**
	 * Sets axiom. If arg0 is null, an exception is thrown
	 * 
	 * @param arg0 String axiom
	 * @return this LSystemBuilder
	 * @throws NullPointerException if arg0 is null
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		Objects.requireNonNull(arg0);

		axiom = arg0;

		return this;
	}

	/**
	 * Sets origin
	 * 
	 * @param arg0 x coordinate
	 * @param arg1 y coordinate
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);

		return this;
	}

	/**
	 * Sets unit length
	 * 
	 * @param arg0 new unit length
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;

		return this;
	}

	/**
	 * Sets unit length degree scaler
	 * 
	 * @param arg0 new unit length degree scaler
	 * @return this LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;

		return this;
	}
}