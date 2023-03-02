import React, { useMemo, useRef } from "react";
import ReactQuill, { Quill } from "react-quill";
import "react-quill/dist/quill.snow.css";
import styled from "styled-components";
import ImageResize from "quill-image-resize";
Quill.register("modules/ImageResize", ImageResize);

const EditorContainer = styled.div`
  .editor {
    height: 210px;
    width: 100%;
  }
`;

function Editor({ bodySideHandle, bodySideBlur, con, onCon }) {
  const quillRef = useRef();

  const imageHandler = async () => {
    const input = document.createElement("input");
    input.setAttribute("type", "file");
    input.setAttribute("accept", "image/*");
    input.click();

    input.onchange = async () => {
      const file = input.files ? input.files[0] : null;
      let data = null;
      const formData = new FormData();

      const quillObj = quillRef?.current?.getEditor();
      const range = quillObj?.getSelection();

      if (file) {
        formData.append("file", file);
        formData.append("resource_type", "raw");

        const responseUpload = await fetch(
          `${process.env.NEXT_PUBLIC_IMAGE_UPLOAD}/upload`,
          { method: "POST", body: formData }
        );

        data = await responseUpload.json();
        if (data.error) {
          console.error(data.error);
        }

        quillObj.editor.insertEmbed(range.index, "image", data?.secure_url);
      }
    };
  };

  const modules = useMemo(
    () => ({
      /* 툴바에서 사용하고 싶은 옵션들을 추가 */
      toolbar: {
        container: [
          [{ header: [1, 2, false] }],
          ["bold", "italic", "underline", "strike", "blockquote", "code-block"],
          [
            { list: "ordered" },
            { list: "bullet" },
            { indent: "-1" },
            { indent: "+1" },
          ],
          ["link", "image"],
          [{ align: [] }, { color: [] }, { background: [] }],
          ["clean"],
        ],
        handlers: {
          image: imageHandler,
        },
      },
      ImageResize: {
        parchment: Quill.import("parchment"),
      },
    }),
    []
  );

  return (
    <EditorContainer>
      <ReactQuill
        className="editor"
        theme="snow"
        con={con}
        onChange={onCon}
        onFocus={bodySideHandle}
        onBlur={bodySideBlur}
        modules={modules}
      />
    </EditorContainer>
  );
}

export default Editor;
