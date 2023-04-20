import { NoticeAlarmItem } from "@/components/notice-alarm-item/";

const ProjectDetailPage = () => {
  return (
    <NoticeAlarmItem>
      <div>정훈 안녕 준하도 안녕</div>
    </NoticeAlarmItem>
  );
};

// // This function gets called at build time
// export async function getStaticPaths() {
//   // Call an external API endpoint to get posts
//   const res = await fetch('https://.../posts')
//   const posts = await res.json()

//   // Get the paths we want to pre-render based on posts
//   const paths = posts.map((post) => ({
//     params: { id: post.id },
//   }))

//   // We'll pre-render only these paths at build time.
//   // { fallback: false } means other routes should 404.
//   return { paths, fallback: false }
// }

export default ProjectDetailPage;
