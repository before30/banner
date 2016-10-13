package me.home.ansi;

import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.util.*;

public class AnsiPropertySource extends PropertySource<AnsiElement> {

	private static final Iterable<MappedEnum<?>> MAPPED_ENUMS;

	static {
		List<MappedEnum<?>> enums = new ArrayList<MappedEnum<?>>();
		enums.add(new MappedEnum<AnsiStyle>("AnsiStyle.", AnsiStyle.class));
		enums.add(new MappedEnum<AnsiColor>("AnsiColor.", AnsiColor.class));
		enums.add(
				new MappedEnum<AnsiBackground>("AnsiBackground.", AnsiBackground.class));
		enums.add(new MappedEnum<AnsiStyle>("Ansi.", AnsiStyle.class));
		enums.add(new MappedEnum<AnsiColor>("Ansi.", AnsiColor.class));
		enums.add(new MappedEnum<AnsiBackground>("Ansi.BG_", AnsiBackground.class));
		MAPPED_ENUMS = Collections.unmodifiableList(enums);
	}

	private final boolean encode;

	/**
	 * Create a new {@link AnsiPropertySource} instance.
	 * @param name the name of the property source
	 * @param encode if the output should be encoded
	 */
	public AnsiPropertySource(String name, boolean encode) {
		super(name);
		this.encode = encode;
	}

	@Override
	public Object getProperty(String name) {
		if (StringUtils.hasLength(name)) {
			for (MappedEnum<?> mappedEnum : MAPPED_ENUMS) {
				if (name.startsWith(mappedEnum.getPrefix())) {
					String enumName = name.substring(mappedEnum.getPrefix().length());
					for (Enum<?> ansiEnum : mappedEnum.getEnums()) {
						if (ansiEnum.name().equals(enumName)) {
							if (this.encode) {
								return AnsiOutput.encode((AnsiElement) ansiEnum);
							}
							return ansiEnum;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Mapping between an enum and the pseudo property source.
	 */
	private static class MappedEnum<E extends Enum<E>> {

		private final String prefix;

		private final Set<E> enums;

		MappedEnum(String prefix, Class<E> enumType) {
			this.prefix = prefix;
			this.enums = EnumSet.allOf(enumType);

		}

		public String getPrefix() {
			return this.prefix;
		}

		public Set<E> getEnums() {
			return this.enums;
		}

	}

}
