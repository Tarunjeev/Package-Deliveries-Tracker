package cmpt213.assignment3.packagedeliveries.gson.extras;

import cmpt213.assignment3.packagedeliveries.model.Package;
import cmpt213.assignment3.packagedeliveries.model.BookPkg;
import cmpt213.assignment3.packagedeliveries.model.ElectronicPkg;
import cmpt213.assignment3.packagedeliveries.model.PerishablePkg;
import cmpt213.assignment3.packagedeliveries.view.MainMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Credits: Google for GSON Lib, RuntimeTypeAdapterFactory class and related classes,
 */
public class AdaptedGson {
	/**
	 * Initiate the read and Write JSON with Package object
	 */
	public static final Gson runtimeConverter = new GsonBuilder()
		.registerTypeAdapter(
			LocalDateTime.class,
			new TypeAdapter<LocalDateTime>() {
				@Override
				public void write(JsonWriter out, LocalDateTime dateTime) throws IOException {
					out.value(MainMenu.DATE_TIME_STYLE_MEDIUM.format(dateTime));
				}

				@Override
				public LocalDateTime read(JsonReader in) throws IOException {
					return LocalDateTime.from(MainMenu.DATE_TIME_STYLE_MEDIUM.parse(in.nextString()));
				}
			}
		)
		.registerTypeAdapterFactory(
			RuntimeTypeAdapterFactory
				.of(Package.class, "type")
				.registerSubtype(BookPkg.class, "book")
				.registerSubtype(ElectronicPkg.class, "electronic")
				.registerSubtype(PerishablePkg.class, "perishable")
		)
		.create();
}