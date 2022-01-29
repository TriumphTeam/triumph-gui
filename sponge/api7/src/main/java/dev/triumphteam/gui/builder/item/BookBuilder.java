/**
 * MIT License
 *
 * Copyright (c) 2021 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.builder.item;

import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.components.util.Legacy;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Item builder for {@link Material#WRITTEN_BOOK} and {@link Material#WRITTEN_BOOK} only
 *
 * @author GabyTM <a href="https://github.com/iGabyTM">https://github.com/iGabyTM</a>
 * @since 3.0.1
 */
public class BookBuilder extends BaseItemBuilder<BookBuilder> {

    private static final Set<ItemType> BOOKS = new HashSet<>(Arrays.asList(ItemTypes.WRITABLE_BOOK, ItemTypes.WRITTEN_BOOK));

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
    @NotNull
    @Contract("_ -> this")
    public BookBuilder author(@Nullable final Component author) {
        this.getItemStack().offer(Keys.BOOK_AUTHOR, Legacy.toText(author));
        return this;
    }

    /**
     * Sets the generation of the book. Removes generation when given null.
     *
     * @param generation the generation to set
     * @return {@link BookBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> this")
    public BookBuilder generation(@Nullable final int generation) {
        this.getItemStack().offer(Keys.GENERATION, generation);
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
    @NotNull
    @Contract("_ -> this")
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
    @NotNull
    @Contract("_ -> this")
    public BookBuilder page(@NotNull final List<Component> pages) {
        final List<Text> textPages = new ArrayList<>();
        for (final Component page : pages) {
            textPages.add(Legacy.toText(page));
        }
        this.getItemStack().offer(Keys.BOOK_PAGES, textPages);
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
    @NotNull
    @Contract("_, _ -> this")
    public BookBuilder page(final int page, @NotNull final Component data) {
        List<Text> textPages = this.getItemStack().get(Keys.BOOK_PAGES).orElse(null);
        if (textPages == null || textPages.isEmpty()) {
            return this;
        }

        if (textPages.size() < (page - 1)) {
            return this;
        }

        final List<Text> modifiedPages = new ArrayList<>();
        int count = 1;
        for (Text bookPage : textPages) {
            if (count == page) {
                modifiedPages.add(Legacy.toText(data));
            } else {
                modifiedPages.add(bookPage);
            }
            count++;
        }
        this.getItemStack().offer(Keys.BOOK_PAGES, modifiedPages);
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
    @NotNull
    @Contract("_ -> this")
    public BookBuilder title(@Nullable Component title) {
        /* TODO
        final BookMeta bookMeta = (BookMeta) getMeta();

        if (title == null) {
            bookMeta.setTitle(null);
            setMeta(bookMeta);
            return this;
        }

        bookMeta.setTitle(Legacy.SERIALIZER.serialize(title));
        setMeta(bookMeta);*/
        return this;
    }

}
