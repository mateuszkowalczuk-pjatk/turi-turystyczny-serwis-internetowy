import { useTranslation } from 'react-i18next'
import Checkbox from '../../Controls/Checkbox'
import styles from './PersonalGender.module.css'
import { GENDER } from '../../../types/gender.ts'

interface Props {
    gender: GENDER | null
    handleGenderChange: (gender: GENDER) => void
}

const PersonalGender = ({ gender, handleGenderChange }: Props) => {
    const { t } = useTranslation()

    return (
        <div className={styles.gender}>
            <Checkbox
                checked={gender === GENDER.FEMALE}
                onChange={() => handleGenderChange(GENDER.FEMALE)}
                text={t('signup-personal.woman')}
            />
            <Checkbox
                checked={gender === GENDER.MALE}
                onChange={() => handleGenderChange(GENDER.MALE)}
                text={t('signup-personal.man')}
            />
        </div>
    )
}

export default PersonalGender
