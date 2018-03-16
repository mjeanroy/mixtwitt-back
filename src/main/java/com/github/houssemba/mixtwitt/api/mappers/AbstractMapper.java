package com.github.houssemba.mixtwitt.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<T, U> {

	public abstract U map(T input);

	public List<U> map(List<T> inputs) {
		return inputs.stream().map(this::map).collect(Collectors.toList());
	}
}
