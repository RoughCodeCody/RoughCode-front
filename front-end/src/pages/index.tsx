import { Head } from "@/components/head";
import { Notifications } from "@/features/notifications";
import { UnauthenticatedLanding } from "@/features/misc";
import { useUser } from "@/features/auth";

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
