/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  compiler: {
    styledComponents: true,
  },
  output: "standalone",
  images: {
    domains: ["picsum.photos"],
  },
};

module.exports = nextConfig;
