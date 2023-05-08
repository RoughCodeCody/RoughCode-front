// import { create } from "zustand";

// type CodeReviewFeedbackData = {
//   selectedLines: number[][];
//   modifiedCode: string;
// };

// type CodeReviewFeedbackDataStore = {
//   CodeReviewFeedbackData: CodeReviewFeedbackData;
//   addSelectedLines: (line: number[]) => void;
//   deleteSelectedLines: (line: number[]) => void;

// };

// export const useCodeReviewFeedbackDataStore =
//   create<CodeReviewFeedbackDataStore>((set) => ({
//     CodeReviewFeedbackData: {
//       selectedLines: [],
//       modifiedCode: "",
//       feedbackContent: "",
//     },
//     addSelectedLines: (line) => {
//       //선택된 라인 추가
//       set((state) => ({
//         CodeReviewFeedbackData: {
//           ...state.CodeReviewFeedbackData,
//           selectedLines: [...state.CodeReviewFeedbackData.selectedLines, line],
//         },
//       }));
//     },

//     deleteSelectedLines: (line) => {
//         set((state) => {
//             let newSelectedLines : number[][]
//             newSelectedLines = state.CodeReviewFeedbackData.selectedLines.filter((item)=> item !== line)
//             return {CodeReviewFeedbackData: {
//                 ...state.CodeReviewFeedbackData,
//                 selectedLines:newSelectedLines
//             }}
//         })
//     }
