
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OpenPerBoard_ {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("disableAt")
    @Expose
    public Integer disableAt;
    @SerializedName("warnAt")
    @Expose
    public Integer warnAt;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("status", status).append("disableAt", disableAt).append("warnAt", warnAt).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(disableAt).append(warnAt).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OpenPerBoard_) == false) {
            return false;
        }
        OpenPerBoard_ rhs = ((OpenPerBoard_) other);
        return new EqualsBuilder().append(disableAt, rhs.disableAt).append(warnAt, rhs.warnAt).append(status, rhs.status).isEquals();
    }

}
