package br.com.fredericci.datatables;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PageResponse<T> implements Page<T>, Serializable {

	private static final long serialVersionUID = 867755909294344406L;

	private final Collection<T> content = new LinkedList<T>();
	private final PageRequest pageable;

	private final long total;

	/**
	 * @param content
	 *            the content of this page
	 * @param pageable
	 *            the paging information
	 * @param total
	 *            the total amount of items available
	 */
	public PageResponse(List<T> content, PageRequest pageable, long total) {

		if (null == content) {
			throw new IllegalArgumentException("Content must not be null!");
		}

		this.content.addAll(content);
		this.total = total;
		this.pageable = pageable;
	}

	/**
	 * Creates a new {@link PageResponse} with the given content. This will result
	 * in the created {@link Page} being identical to the entire {@link List}.
	 * 
	 * @param content
	 */
	public PageResponse(List<T> content) {

		this(content, null, (null == content) ? 0 : content.size());
	}

	public int getNumber() {
		return pageable == null ? 0 : pageable.getPageNumber();
	}

	public int getSize() {
		return pageable == null ? 0 : pageable.getSize();
	}

	public int getTotalPages() {

		return getSize() == 0 ? 0 : (int) Math.ceil((double) total / (double) getSize());
	}

	public int getNumberOfElements() {

		return content.size();
	}

	public long getTotalElements() {

		return total;
	}

	public boolean hasPreviousPage() {

		return getNumber() > 0;
	}

	public boolean isFirstPage() {

		return !hasPreviousPage();
	}

	public boolean hasNextPage() {

		return ((getNumber() + 1) * getSize()) < total;
	}

	public boolean isLastPage() {

		return !hasNextPage();
	}

	public Iterator<T> iterator() {

		return content.iterator();
	}

	public Collection<T> getContent() {

		return Collections.unmodifiableCollection(content);
	}

	public boolean hasContent() {

		return !content.isEmpty();
	}

	@Override
	public String toString() {

		String contentType = "UNKNOWN";

		if (content.size() > 0) {
			contentType = content.iterator().next().getClass().getName();
		}

		return String.format("Page %s of %d containing %s instances", getNumber(), getTotalPages(), contentType);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PageResponse<?>)) {
			return false;
		}

		PageResponse<?> that = (PageResponse<?>) obj;

		boolean totalEqual = this.total == that.total;
		boolean contentEqual = this.content.equals(that.content);
		boolean pageableEqual = this.pageable == null ? that.pageable == null : this.pageable.equals(that.pageable);

		return totalEqual && contentEqual && pageableEqual;
	}

	@Override
	public int hashCode() {

		int result = 17;

		result = 31 * result + (int) (total ^ total >>> 32);
		result = 31 * result + (pageable == null ? 0 : pageable.hashCode());
		result = 31 * result + content.hashCode();

		return result;
	}
}