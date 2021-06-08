package hu.flowacademy.band;

import java.time.format.DateTimeFormatter;

/**
 * Olyan objektumok gyűjtő helye (lehet akár), amik általánosságban kellhet, de hogy ne kelljen
 * több helyen inicializálni ugyanazzal a paraméterekkel, így "globálissá" tettem egy ilyennel.
 *
 * Így csak meghívom az adott attribútumot és jónapot... :)
 */
public class ConstantValues {
    public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
