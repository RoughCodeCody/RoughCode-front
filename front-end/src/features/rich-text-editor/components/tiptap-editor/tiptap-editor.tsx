import { useEditor, EditorContent } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import CharacterCount from "@tiptap/extension-character-count";
import Highlight from "@tiptap/extension-highlight";
import TaskItem from "@tiptap/extension-task-item";
import TaskList from "@tiptap/extension-task-list";

import { TiptapMenuBar } from "../tiptap-menu-bar";
import { Editor } from "./style";

type TiptapEditorProps = {
  onChange: (...event: any[]) => void;
  minHeight?: string;
};

export const TiptapEditor = ({
  onChange,
  minHeight = "10rem",
}: TiptapEditorProps) => {
  const editor = useEditor({
    extensions: [
      StarterKit,
      Highlight,
      TaskList,
      TaskItem,
      CharacterCount.configure({
        limit: 10000,
      }),
    ],
    onUpdate: ({ editor }) => {
      const html = editor.getHTML();
      onChange(html);
    },
  });

  return (
    <Editor minHeight={minHeight}>
      {editor && <TiptapMenuBar editor={editor} />}
      <EditorContent className="editor__content" editor={editor} />
    </Editor>
  );
};
