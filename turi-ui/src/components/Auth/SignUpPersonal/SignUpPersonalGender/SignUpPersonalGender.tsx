import { useTranslation } from 'react-i18next'
import { useState } from 'react'
import Checkbox from '../../../Controls/Checkbox'
import styles from './SignUpPersonalGender.module.css'

const GENDER = {
    MAN: 'man',
    WOMAN: 'woman',
};

const SignUpPersonalGender = () => {
    const { t } = useTranslation();
    const [selectedGender, setSelectedGender] = useState<string | null>(null);

    const handleGenderChange = (gender: string) => {
        setSelectedGender(gender);
    };

    return (
        <div className={styles.gender}>
            <Checkbox
                checked={selectedGender === GENDER.WOMAN}
                onChange={() => handleGenderChange(GENDER.WOMAN)}
                text={t('signup-personal.woman')}
            />
            <Checkbox
                checked={selectedGender === GENDER.MAN}
                onChange={() => handleGenderChange(GENDER.MAN)}
                text={t('signup-personal.man')}
            />
        </div>
    );
}

export default SignUpPersonalGender;