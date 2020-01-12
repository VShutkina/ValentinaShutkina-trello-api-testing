
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Limits {

    @SerializedName("attachments")
    @Expose
    public Attachments attachments;
    @SerializedName("boards")
    @Expose
    public Boards boards;
    @SerializedName("cards")
    @Expose
    public Cards cards;
    @SerializedName("checklists")
    @Expose
    public Checklists checklists;
    @SerializedName("customFields")
    @Expose
    public CustomFields customFields;
    @SerializedName("labels")
    @Expose
    public Labels labels;
    @SerializedName("lists")
    @Expose
    public Lists lists;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("attachments", attachments).append("boards", boards).append("cards", cards).append("checklists", checklists).append("customFields", customFields).append("labels", labels).append("lists", lists).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(checklists).append(attachments).append(cards).append(customFields).append(lists).append(boards).append(labels).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Limits) == false) {
            return false;
        }
        Limits rhs = ((Limits) other);
        return new EqualsBuilder().append(checklists, rhs.checklists).append(attachments, rhs.attachments).append(cards, rhs.cards).append(customFields, rhs.customFields).append(lists, rhs.lists).append(boards, rhs.boards).append(labels, rhs.labels).isEquals();
    }

}
