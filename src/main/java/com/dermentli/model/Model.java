package com.dermentli.model;

import lombok.Data;

import java.nio.file.Path;
import java.util.List;

@Data
public class Model {
    private Path resource;
    private int linesCount;
    private List<Model> subResources;

    public static Builder builder() {
        return new Model().new Builder();
    }

    public String toString() {
        return "LinesStats{" +
                "resource=" + resource +
                ", linesCount=" + linesCount +
                ", subResources=" + subResources +
                "}";
    }

    public class Builder {
        private Builder() {
        }

        public Builder resource(Path path) {
            Model.this.resource = path;
            return this;
        }

        public Builder linesCount(int total) {
            Model.this.linesCount = total;
            return this;
        }

        public Builder subResources(List<Model> resources) {
            if (resources == null) {
                throw new IllegalArgumentException("sub resources can't be null");
            }
            Model.this.subResources = resources;
            return this;
        }

        public Model build() {
            return Model.this;
        }

    }
}

