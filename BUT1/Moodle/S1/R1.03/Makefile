OUTDIR= output
SRC= $(wildcard *.md)
PDF= $(addprefix $(OUTDIR)/,$(SRC:.md=.pdf))
HTML= $(addprefix  $(OUTDIR)/,$(SRC:.md=.html))


all: $(PDF) $(HTML)

pdf: $(PDF)

html: $(HTML)

directories: $(OUTDIR)

$(OUTDIR):
	mkdir $(OUTDIR)

$(OUTDIR)/%.html: %.md $(OUTDIR)
	pandoc --from=markdown+lists_without_preceding_blankline $< -o $@ -s

$(OUTDIR)/%.pdf: $(OUTDIR)/%.html
	wkhtmltopdf $< $@

clean:
	\rm -rf *.html *.pdf $(OUTDIR)
