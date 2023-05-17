import { useQuery } from "@tanstack/react-query";

import { axios } from "@/lib/axios";
import { ExtractFnReturnType, QueryConfig } from "@/lib/react-query";

interface GetStatCardProps {
  userName: string;
}

export const getStatCard = ({
  userName,
}: GetStatCardProps): Promise<string> => {
  return axios.get(`/mypage/stat?userName=${userName}`);
};

type QueryFnType = typeof getStatCard;

type useStatCardOptions = {
  userName: string;
  config?: QueryConfig<QueryFnType>;
};

export const useStatCard = ({ userName, config }: useStatCardOptions) => {
  return useQuery<ExtractFnReturnType<QueryFnType>>({
    queryKey: ["statCard", userName],
    queryFn: () => getStatCard({ userName }),
    enabled: !!userName,
    ...config,
  });
};
