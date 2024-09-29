import { ReactNode } from "react";
import Dashboard from "../../components/Dashboard";
import Proposition from "../../components/Proposition";
import styles from './HomeLayout.module.css'

interface Props {
    header: ReactNode;
    information: ReactNode;
    footer: ReactNode;
}

const HomeLayout = ({ header, information, footer }: Props) => {
    return (
        <div className={styles.home}>
            { header }
            <Dashboard />
            { information }
            <Proposition />
            { footer }
        </div>
    )
}

export default HomeLayout;