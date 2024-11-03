CREATE EXTENSION pg_trgm;
CREATE INDEX title_trgm_idx ON Post USING gin (title gin_trgm_ops);
