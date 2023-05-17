export const convertGitHubUrl = (url: string) => {
  const GITHUB_API_URL = "https://api.github.com/repos/";
  const [ownerRepo, subUrl] = url
    .replace("https://github.com/", "")
    .split("blob/");
  const branchName = subUrl?.split("/")[0];
  const filePath = subUrl?.substring(branchName.length);

  const convertedUrl = `${GITHUB_API_URL}${ownerRepo}contents${filePath}?ref=${branchName}`;

  return convertedUrl;
};
