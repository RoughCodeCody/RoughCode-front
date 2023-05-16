import { Head } from "@/components/head";
import { useUser } from "@/features/auth";
import { UnauthenticatedLanding } from "@/features/misc";
import { Notifications } from "@/features/notifications";

export default function Home() {
  const userQuery = useUser();
  return (
    <>
      <Head description="개발새발 홈" />
      {userQuery.isLoading && "Loading..."}
      {userQuery.data && userQuery.data.nickname.length === 0 && (
        <UnauthenticatedLanding />
      )}
      {userQuery.data && userQuery.data.nickname.length !== 0 && (
        <Notifications />
      )}
    </>
  );
}
