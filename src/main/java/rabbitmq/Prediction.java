package rabbitmq;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Prediction {
	private String content;

	public Prediction() {
		
	}

	public String getContent() {
		return content;
	}

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
