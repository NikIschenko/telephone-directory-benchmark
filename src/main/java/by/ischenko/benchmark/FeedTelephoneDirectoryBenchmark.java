package by.ischenko.benchmark;

import by.ischenko.ContactsFeeder;
import by.ischenko.DirectoryType;
import by.ischenko.TelephoneDirectory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(jvmArgs = {"-Xms6G", "-Xmx6G"})
public class FeedTelephoneDirectoryBenchmark {
	@Benchmark
	public TelephoneDirectory generateDirectory(TestState testState, Blackhole blackhole) throws IOException {
		TelephoneDirectory telephoneDirectory = generateDirectoryByType(testState.directoryType.create(), testState.dictionaryCapacity);
		blackhole.consume(telephoneDirectory);
		return telephoneDirectory;
	}

	public TelephoneDirectory generateDirectoryByType(TelephoneDirectory telephoneDirectory, int capacity) throws IOException {
		ContactsFeeder contactsFeeder = new ContactsFeeder("first-name.txt", "last-name.txt");
		contactsFeeder.writeFullNames(telephoneDirectory, capacity);
		return telephoneDirectory;
	}

	@State(Scope.Benchmark)
	public static class TestState {
		public int dictionaryCapacity = 10_000_000;
		@Param({"MAP", "TRIE"})
		public DirectoryType directoryType;
	}
}
