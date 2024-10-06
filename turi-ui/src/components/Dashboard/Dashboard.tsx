import {useTranslation} from "react-i18next";
import HeaderText from "../Typography/HeaderText";
import Text from "../Typography/Text";
import SearchPanel from "../SearchPanel/SearchPanel.tsx";
import SearchTypeButtons from "../SearchTypeButtons";
import styles from './Dashboard.module.css';

const Dashboard = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.dashboard}>
            <div className={styles.text}>
                <HeaderText
                    text={t('dashboard.header-text')}
                />
                <Text
                    text={t('dashboard.text')}
                />
            </div>
            <div className={styles.search}>
                <SearchPanel />
                <SearchTypeButtons />
            </div>
        </div>
    )
}

export default Dashboard;