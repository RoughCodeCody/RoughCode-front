/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  compiler: {
    styledComponents: true,
  },
  output: "standalone",
  images: {
    domains: [
      "img.rough-code.com",
      "roughcode.s3.ap-northeast-2.amazonaws.com",
      "d2swdwg2kwda2j.cloudfront.net",
    ],
  },
};

module.exports = nextConfig;
