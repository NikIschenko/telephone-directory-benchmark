package by.ischenko.benchmark;

import by.ischenko.DirectoryType;
import by.ischenko.TelephoneDirectory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1, warmups = 1, jvmArgs = {"-Xms6G", "-Xmx6G"})
public class FindContactsBenchmark {
	@Benchmark
	public long findRandomContacts(TestState testState, Blackhole blackhole) {
		TelephoneDirectory telephoneDirectory = testState.trieDirectory;
		List<String> randomFullNames = testState.randomFullNames;

		long countOfFound = randomFullNames.stream()
				.map(telephoneDirectory::find)
				.filter(t -> t > -1)
				.count();

		resultPrinting(testState, countOfFound);

		blackhole.consume(countOfFound);
		return countOfFound;
	}

	private void resultPrinting(TestState testState, long countOfFound) {
		System.out.println("===" + testState.directoryType.name() + " result===");
		System.out.println(" * count of contacts: " + testState.dictionaryCapacity);
		System.out.println(" * count of sought: " + testState.randomFullNames.size());
		System.out.println(" * were found: " + countOfFound);
	}

	@State(Scope.Benchmark)
	public static class TestState {
		@Param({"MAP", "TRIE"})
		public DirectoryType directoryType;
		public int dictionaryCapacity = 10_000_000;
		public List<String> randomFullNames;
		public TelephoneDirectory trieDirectory;

		@Setup(Level.Trial)
		public void setup() throws IOException, URISyntaxException {
			randomFullNames = getNamesFromFile("random-full-name.txt");
			trieDirectory = generateTrieContacts();
			System.out.println("directoryType = " + directoryType);
		}

		private TelephoneDirectory generateTrieContacts() throws IOException {
			TelephoneDirectory trie = directoryType.create();
			new FeedTelephoneDirectoryBenchmark().generateDirectoryByType(trie, dictionaryCapacity);
			return trie;
		}

		private List<String> getNamesFromFile(String fileName) throws URISyntaxException, IOException {
			try (Stream<String> fullNamesStream = Files.lines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()))) {
				return fullNamesStream.collect(Collectors.toList());
			}
		}
	}
}
