package dev.triumphteam.gui.builder.item;

import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.components.util.Legacy;
import dev.triumphteam.gui.components.util.VersionHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Item builder for {@link Material#WRITTEN_BOOK} and {@link Material#WRITTEN_BOOK} only
 *
 * @author GabyTM <a href="https://github.com/iGabyTM">https://github.com/iGabyTM</a>
 * @since 3.0.1
 */
public class BookBuilder extends BaseItemBuilder<BookBuilder> {

    private static final EnumSet<Material> BOOKS = EnumSet.of(Material.WRITABLE_BOOK, Material.WRITTEN_BOOK);

    BookBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
        if (!BOOKS.contains(itemStack.getType())) {
            throw new GuiException("BookBuilder requires the material to be a WRITABLE_BOOK/WRITTEN_BOOK!");
        }
    }

    /**
     * Sets the author of the book. Removes author when given null.
     *
     * @param author the author to set
     * @return {@link BookBuilder}
     * @since 3.0.1
     */
    public BookBuilder author(@Nullable final Component author) {
        final BookMeta bookMeta = (BookMeta) getMeta();

        if (author == null) {
            bookMeta.setAuthor(null);
            setMeta(bookMeta);
            return this;
        }

        bookMeta.setAuthor(Legacy.SERIALIZER.serialize(author));
        setMeta(bookMeta);
        return this;
    }

    /**
     * Sets the generation of the book. Removes generation when given null.
     *
     * @param generation the generation to set
     * @return {@link BookBuilder}
     * @since 3.0.1
     */
    public BookBuilder generation(@Nullable final BookMeta.Generation generation) {
        final BookMeta bookMeta = (BookMeta) getMeta();

        bookMeta.setGeneration(generation);
        setMeta(bookMeta);
        return this;
    }

    /**
     * Adds new pages to the end of the book. Up to a maximum of 50 pages with
     * 256 characters per page.
     *
     * @param pages list of pages
     * @return {@link BookBuilder}
     * @since 3.0.1
     */
    public BookBuilder page(@NotNull final Component... pages) {
        return page(Arrays.asList(pages));
    }

    /**
     * Adds new pages to the end of the book. Up to a maximum of 50 pages with
     * 256 characters per page.
     *
     * @param pages list of pages
     * @return {@link BookBuilder}
     * @since 3.0.1
     */
    public BookBuilder page(@NotNull final List<Component> pages) {
        final BookMeta bookMeta = (BookMeta) getMeta();

        for (final Component page : pages) {
            bookMeta.addPage(Legacy.SERIALIZER.serialize(page));
        }

        setMeta(bookMeta);
        return this;
    }

    /**
     * Sets the specified page in the book. Pages of the book must be
     * contiguous.
     * <p>
     * The data can be up to 256 characters in length, additional characters
     * are truncated.
     * <p>
     * Pages are 1-indexed.
     *
     * @param page the page number to set, in range [1, {@link BookMeta#getPageCount()}]
     * @param data the data to set for that page
     * @return {@link BookBuilder}
     * @since 3.0.1
     */
    public BookBuilder page(final int page, @NotNull final Component data) {
        final BookMeta bookMeta = (BookMeta) getMeta();

        bookMeta.setPage(page, Legacy.SERIALIZER.serialize(data));
        setMeta(bookMeta);
        return this;
    }

    /**
     * Sets the title of the book.
     * <p>
     * Limited to 32 characters. Removes title when given null.
     *
     * @param title the title to set
     * @return {@link BookBuilder}
     * @since 3.0.1
     */
    public BookBuilder title(@Nullable Component title) {
        final BookMeta bookMeta = (BookMeta) getMeta();

        if (title == null) {
            bookMeta.setTitle(null);
            setMeta(bookMeta);
            return this;
        }

        bookMeta.setTitle(Legacy.SERIALIZER.serialize(title));
        setMeta(bookMeta);
        return this;
    }

}
