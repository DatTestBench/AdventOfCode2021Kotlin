data class MovementCommand(val direction: String, val magnitude: Int)

fun Vec2i.applyMovement(movementCommand: MovementCommand) {
	val (direction, magnitude) = movementCommand
	when (direction) {
		"forward" -> x += magnitude
		"up" -> y -= magnitude // We're dealing with depth here
		"down" -> y += magnitude
		else -> Vec2i()
	}
}

data class MovementWithAim(val position: Vec2i = Vec2i(), var aim: Int = 0) {
	fun applyMovement(movementCommand: MovementCommand) {
		val (direction, magnitude) = movementCommand
		when (direction) {
			"forward" -> {
				position.x += magnitude
				position.y += aim * magnitude
			}
			"up" -> aim -= magnitude
			"down" -> aim += magnitude
		}
	}
}

fun day02() {
	fun parse(input: List<String>): List<MovementCommand> {
		return input.map {
			val (direction, magnitude) = it
				.split(" ")
				.zipWithNext()
				.first()
			MovementCommand(direction, magnitude.toInt())
		}
	}

	fun part1(input: List<MovementCommand>): Int {
		val position = Vec2i()

		for (command in input) {
			position.applyMovement(command)
		}

		println("Pos: $position")
		return position.x * position.y
	}

	fun part2(input: List<MovementCommand>): Int {
		val subMovement = MovementWithAim()

		for (command in input) {
			subMovement.applyMovement(command)
		}

		println("Sub: $subMovement")
		return subMovement.position.x * subMovement.position.y
	}

	val day = dayHeader(object {})

	val test = parse("""
		forward 5
		down 5
		forward 8
		up 3
		down 8
		forward 2
		"""
		.trimIndent()
		.split("\n"))

	val input = parse(readInputOnline(2021, day))

	outputTestResult(1, part1(test))
	outputPuzzleResult(1, part1(input))

	outputTestResult(2, part2(test))
	outputPuzzleResult(2, part2(input))
}
