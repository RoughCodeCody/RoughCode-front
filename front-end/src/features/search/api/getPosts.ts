// import { useQuery } from "@tanstack/react-query";

// import { axios } from "@/lib/axios";
// import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

// import { ProjectsList } from "../types";

// // get 요청으로 바꿔야함, 오히려 쉬워짐
// // 나머지 세개의 파람스만 추가하면 됨
// // 그리고 getCodeList는 새로 파일 만들어서 하자

// export const getProjects = ({
//   sort,
//   page,
//   size,
// }: {
//   sort: string;
//   page: number;
//   size: number;
// }): Promise<ProjectList[]> => {
//   return axios.get(`/project`, {
//     params: {
//       sort,
//       page,
//       size,
//     },
//   });
// };

// type QueryFnType = typeof getProjects;

// type UseProjectsOptions = {
//   sort: string;
//   page: number;
//   size: number;
//   config?: QueryConfig<QueryFnType>;
// };

// export const useComments = ({
//   sort,
//   page,
//   size,
//   config,
// }: UseProjectsOptions) => {
//   return useQuery<ExtractFnReturnType<QueryFnType>>({
//     queryKey: ["comments", sort, page, size],
//     queryFn: () => getProjects({ sort, page, size }),
//     ...config,
//   });
// };
