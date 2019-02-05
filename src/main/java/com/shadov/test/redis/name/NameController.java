package com.shadov.test.redis.name;

import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/names")
public class NameController {
	@Autowired
	private NameRepository nameRepository;

	@GetMapping("/query")
	public ResponseEntity<Map<String, Map<String, String>>> query() {
		final Iterable<NameEntry> all = nameRepository.findAll();
		return ResponseEntity.ok(List.ofAll(all).peek(System.out::println).toMap(NameEntry::getGroup, NameEntry::getCatalog));
	}

	@GetMapping("/clear")
	public ResponseEntity<Void> clear() {
		nameRepository.deleteAll();
		return ResponseEntity.ok().build();
	}

	@PostMapping("/add/{name}")
	public ResponseEntity<NameEntry> add(@PathVariable("name") String name, @RequestBody Map<String, String> requestBody) {
		NameEntry nameEntry = NameEntry.noId(name, requestBody.toJavaMap());
		NameEntry saved = nameRepository.save(nameEntry);
		System.out.println(saved);

		return ResponseEntity.ok(saved);
	}

	@GetMapping("/addTo/{name}/{catalog}/{value}")
	public ResponseEntity<NameEntry> addTo(@PathVariable("name") String name, @PathVariable("catalog") String catalog, @PathVariable("value") String value) {
		return ResponseEntity.ok(
				nameRepository.findByGroup(name)
						.peek(System.out::println)
						.map(nameEntry -> nameEntry.addToCatalog(catalog, value))
						.map(nameRepository::save)
						.getOrElseThrow(() -> new IllegalArgumentException("Entry with given name not found"))
		);
	}

	@GetMapping("/get/{name}")
	public ResponseEntity<NameEntry> get(@PathVariable("name") String name) {
		return ResponseEntity.ok(nameRepository.findByGroup(name).peek(System.out::println).getOrElseThrow(() -> new IllegalArgumentException("Not found")));
	}
}
