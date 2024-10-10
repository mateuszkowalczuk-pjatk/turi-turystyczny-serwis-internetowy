import { useTranslation } from "react-i18next";
import Input from "../../../Input";
import styles from './SearchInput.module.css';

const SearchInput = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.input}>
            <Input
                text={t('dashboard.placeholder')}
            />
        </div>
    )
}

export default SearchInput;